#!python/3.7.1

from pandas import read_csv
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.dates import DateFormatter, date2num
from pathlib import Path

import csv

from datetime import datetime
import datetime
		
class data_analysis:
	def predict_batch_run_time(self, csv_path, csv_file, **kwargs):
		try:
			data = read_csv(csv_path + csv_file)
		except:
			return False
		batch_details = data[data['CoverageLabel'].isin([kwargs['BatchName']])][data[data['CoverageLabel'].isin([kwargs['BatchName']])]['Application'].isin([kwargs['Application']])]
		if len(batch_details == 0):
			return False
		latest_batch = batch_details.iloc[-1]
		#row_index = len(batch_details.index)
		dict_run_time_stats_by_host_count = {}
		list_host_count, list_median_run_time_by_test_case_count, list_median_actual_batch_run_time = [], [], []
		for host_count, group in batch_details.groupby('Hosts'):
			batch_details_by_host_count = batch_details[batch_details['Hosts'].isin([host_count])]
			batch_details_by_host_count['RunTime'] = list(map(lambda item: (datetime.datetime.strptime(item, '%H:%M:%S') - datetime.datetime.strptime('00:00:00', '%H:%M:%S')), batch_details_by_host_count['RunTime']))
			batch_details_by_host_count['TestCases'] = list(map(lambda item: item, batch_details_by_host_count['TestCases']))
			batch_details_by_host_count['ActualUtilisationTimePerHost'] = list(map(lambda item: (datetime.datetime.strptime(item, '%H:%M:%S') - datetime.datetime.strptime('00:00:00', '%H:%M:%S')), batch_details_by_host_count['ActualUtilisationTimePerHost']))
			median_run_time = batch_details_by_host_count['RunTime'].median()
			median_test_case_count = batch_details_by_host_count['TestCases'].median()
			median_run_time_by_test_case_count = (median_run_time / int(median_test_case_count)) * int(kwargs['TestCaseCount'])
			median_run_time_by_test_case_count = median_run_time_by_test_case_count - datetime.timedelta(microseconds=median_run_time_by_test_case_count.microseconds)
			median_actual_batch_run_time = batch_details_by_host_count['ActualUtilisationTimePerHost'].median()
			dict_run_time_stats_by_host_count[host_count] = [median_run_time_by_test_case_count, median_actual_batch_run_time]
			
			list_host_count.append(host_count)
			list_median_run_time_by_test_case_count.append(median_run_time_by_test_case_count.total_seconds() / 3600)
			list_median_actual_batch_run_time.append(median_actual_batch_run_time.total_seconds() / 3600)
			
			if int(host_count) == int(latest_batch['Hosts']):
				row_index = data.index[data['BatchId'] == latest_batch['BatchId']]
				csv_handling().csv_add_data_to_existing_row(csv_path, csv_file, ['PredictedRunTime'], [median_run_time_by_test_case_count], row_index)
				
		self.plot_graph(list_host_count, [list_median_run_time_by_test_case_count, list_median_actual_batch_run_time], ['Predicted Run Time', 'Predicted Utilisation Time Per Host'], 'upper right', 'No. of Hosts', 'Batch Execution Time', csv_path + 'RunTime.png')
		return dict_run_time_stats_by_host_count
		
	def predict_test_case_run_time(self, csv_path, csv_file, **kwargs):
		try:
			data = read_csv(csv_path + csv_file)
		except:
			return False
		
		tc_id = data[data['TCID'].isin([kwargs['TCID']])][data[data['TCID'].isin([kwargs['TCID']])]['Application'].isin([kwargs['Application']])]
		tc_id['datetime'] = list(map(lambda item: (datetime.datetime.strptime(item, '%H:%M:%S') - datetime.datetime.strptime('00:00:00', '%H:%M:%S')), tc_id['RunTime']))
		return tc_id['datetime'].median()
		
	def predict_test_case_success_rate(self, csv_path, csv_file, **kwargs):
		try:
			data = read_csv(csv_path + csv_file)
		except:
			return False
		
		tc_id = data[data['TCID'].isin([kwargs['TCID']])][data[data['TCID'].isin([kwargs['TCID']])]['Application'].isin([kwargs['Application']])]
		abort_rate = round((tc_id['Status'].str.count('ABORTED').sum() / tc_id['Status'].size) * 100, 2)
		fail_rate = round((tc_id['Status'].str.count('FAIL').sum() / tc_id['Status'].size) * 100, 2)
		pass_rate = round((tc_id['Status'].str.count('PASS').sum() / tc_id['Status'].size) * 100, 2)
		dict_run_rates = {}
		dict_run_rates['ABORT'] = abort_rate
		dict_run_rates['FAIL'] = fail_rate
		dict_run_rates['PASS'] = pass_rate
		return dict_run_rates
		
	def plot_graph(self, x_axis_data, list_y_axis_data, list_legend, legend_location, x_axis_label, y_axis_label, graph_path):
		for item in list_y_axis_data:
			plt.plot(x_axis_data, item)
		plt.legend(list_legend, loc=legend_location)
		plt.xlabel(x_axis_label)
		plt.ylabel(y_axis_label)
		plt.savefig(graph_path)
		
class csv_handling:
	def csv_add_data(self, csv_path, csv_file, header_data, row_data):
		file = Path(csv_path + csv_file)
		if not file.is_file():
			self.csv_write(csv_path, csv_file, header_data, "w")
		self.csv_write(csv_path, csv_file, row_data, "a")
		
	def csv_write(self, csv_path, csv_file, data, mode):
		with open(csv_path + csv_file, mode) as csv_file:
			writer = csv.writer(csv_file, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
			writer.writerow(data)
			
	def save_batch_data(self, csv_path, csv_file, list_col_name, list_data, batch_id):
		df = pd.read_csv(csv_path + csv_file)
		row_index = df.index[df['BatchId'] == batch_id]
		self.csv_add_data_to_existing_row(csv_path, csv_file, list_col_name, list_data, row_index)
		
	def csv_add_data_to_existing_row(self, csv_path, csv_file, list_col_name, list_data, row_index):
		df = pd.read_csv(csv_path + csv_file)
		irow = 0
		for item in list_col_name:
			df.set_value(row_index, list_col_name[irow], list_data[irow])
			irow = irow + 1
		df.to_csv(csv_path + csv_file, index=False)
