#!python/3.7.1

from pandas import read_csv
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.dates import DateFormatter, date2num
from pathlib import Path

import csv

from datetime import datetime
import datetime

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
		
		