#!/usr/bin/python python3.7.1
# -*- coding: <encoding name> -*-

import os.path
import os, inspect
import importlib.util
import datetime
		
class prediction_stats_generator:

	def __init__(self):
		self.header = '<title>PARSER Prediction Stats</title>' \
		'<link rel="icon" href="PARSER_LOGO.ico" type="image/x-icon" />' \
		'<link rel="shortcut icon" href="PARSER_LOGO.ico" type="image/x-icon" /></head>' \
		'<center><font color="#008080" size="5" style="font-family:Bahnschrift;"><b>PARSER Prediction Stats</b></font></center>' \
		'<body style="font-family:Bahnschrift;background-color:white;"><br><b><font color="black">'
		
		self.table_1 = '<table><center><body style="font-family:Bahnschrift;background-color:white;"><b>' \
		'<td bgcolor="#D3D3D3" align="center"><font color="#2F4F4F">No. of Hosts</td>' \
		'<td bgcolor="#D3D3D3" align="center"><font color="#2F4F4F">Predicted Run Time</td>' \
		'<td bgcolor="#D3D3D3" align="center"><font color="#2F4F4F">Predicted Utilisation Time Per Host</td>' \
		'</b></center><font color="white">'
		
		self.row_template_1 = '<tr bgcolor="#008080"><td><font color="white">Hosts</font></td>' \
		'<td><font color="white">PredictedRunTime</font></td>' \
		'<td><font color="white">UtilisationTimePerHost</font></td></tr>'
		
		self.table_2 = '<table><center><body style="font-family:Bahnschrift;background-color:white;"><b>' \
		'<td bgcolor="#D3D3D3" align="center"><font color="#2F4F4F">TCID</td>' \
		'<td bgcolor="#D3D3D3" align="center"><font color="#2F4F4F">Predicted Run Time</td>' \
		'<td bgcolor="#D3D3D3" align="center"><font color="#2F4F4F">Predicted Pass %</td>' \
		'<td bgcolor="#D3D3D3" align="center"><font color="#2F4F4F">Predicted Fail %</td>' \
		'<td bgcolor="#D3D3D3" align="center"><font color="#2F4F4F">Predicted Abort %</td>' \
		'</b></center><font color="white">'
		
		self.row_template_2 = '<tr bgcolor="#008080"><td><font color="white">TCID</font></td>' \
		'<td><font color="white">PredictedRunTime</font></td>' \
		'<td><font color="white">PredictedPass</font></td>' \
		'<td><font color="white">PredictedFail</font></td>' \
		'<td><font color="white">PredictedAbort</font></td></tr>'
		
	def get_prediction_stats(self, csv_path, project, application, batch_name, list_tcids, dict_run_time_stats_by_host_count, list_predicted_test_case_run_time, list_predicted_test_case_success_rate):
		batch_info = 'Project: ' + project + '</br>' \
		+ 'Application: ' + application + '</br>' \
		+ 'Batch Name: ' + batch_name + '</br>' \
		+ 'Test Cases in Batch: ' + str(len(list_tcids)) + '</br>' \
		+ '<br><font color="#696969">Batch Stats: </font>'
		
		row_1, row_2 = '', ''
		
		for host_count, run_time in dict_run_time_stats_by_host_count.items():
			row_1= row_1 + self.row_template_1 \
			.replace('Hosts', str(host_count).replace('.0', '')) \
			.replace('PredictedRunTime', str(run_time[0]).split('.')[0].replace('0 days ', '')) \
			.replace('UtilisationTimePerHost', str(run_time[1]).split('.')[0].replace('0 days ', ''))
		
		itr = 0
		for item in list_tcids:
			row_2 = row_2 + self.row_template_2 \
			.replace('TCID', list_tcids[itr]) \
			.replace('PredictedRunTime', list_predicted_test_case_run_time[itr].split('.')[0].replace('0 days ', '')) \
			.replace('PredictedPass', list_predicted_test_case_success_rate[itr].get('PASS')) \
			.replace('PredictedFail', list_predicted_test_case_success_rate[itr].get('FAIL')) \
			.replace('PredictedAbort', list_predicted_test_case_success_rate[itr].get('ABORT'))
			itr = itr + 1
		
		img = '<img src="file:/' + csv_path + 'RunTime.png">'
		
		self.body = self.header + batch_info + self.table_1 + row_1 + '</table>' + img \
		+ '<br>' + '<font color="#696969">Test Case Stats:</font>' + self.table_2 + self.filter + row_2 + '</table>'
		
		return '<html><head><meta http-equiv="Content-Type" content="text/html; charset=us-ascii">' + self.body + '</html>'
		
	def error_text(self, text):
		return '<html><head><title>PARSER Prediction Stats</title><meta http-equiv="Content-Type" content="text/html; charset=us-ascii"></head>' \
		'<center><font color="#008080" size="5" style="font-family:Bahnschrift;"><b>PARSER Prediction Stats</b></font></center>' \
		'<font color="#FF0000" size="4"><br>' + text + '</font></html>'
