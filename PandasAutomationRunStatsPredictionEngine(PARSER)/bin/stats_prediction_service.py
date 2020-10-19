#!/usr/bin/python python3.7.1
# -*- coding: <encoding name> -*-

import os.path
import os, sys,inspect
import argparse
import importlib.util
import ast
import json
from json import loads

import prediction_engine
import db_calls

class stats_prediction_service:

	def generate_prediction_stats(self, project, application, batch_name, batch_id, csv_path):
		csv_path = csv_path.replace('path:', '')
		init_predictions_stats_generator = prediction_stats_generator()
		
		kwargs = {'BatchId': batch_id}
		query = "select count(*) from Execution where BatchId = '" + batch_id + "'"
		test_case_count = db_calls.get(query)
		
		csv_file = project + '_Batch_Stats.csv'
		kwargs = {'BatchName': batch_name, 'Application': application, 'TestCaseCount': test_case_count}
		dict_run_time_stats_by_host_count = prediction_engine.data_analysis().predict_batch_run_time(csv_path, csv_file, kwargs)
		if not dict_run_time_stats_by_host_count:
			retrun init_predictions_stats_generator.error_text('ERROR: Could NOT retrieve historical data for predictions')
			
		list_tcids, list_predicted_test_case_run_time, list_predicted_test_case_success_rate = [], [], []
		csv_file = project + '_Test_Case_Stats.csv'
		dict_execution_cols = db_calls.get_table_col_details()
		
		kwargs = {'BatchId': batch_id}
		query = "select * from Execution where BatchId = '" + batch_id + "'"
		rows = db_calls.get(query)
		if rows is not None:
			list_rows = rows.split('|')
			for item_row in list_rows:
				list_fields = item_row.split('#')
				tcid = list_fields[dict_execution_cols['TCID']]
				list_tcids.append(tcid)
				kwargs = {'TCID': tcid, 'Application': application}
				
				response_1 = prediction_engine.data_analysis().predict_test_case_run_time(csv_path, csv_file, kwargs)
				if not response_1:
					return init_predictions_stats_generator.error_text('ERROR: Could NOT retrieve historical data for predictions')
					
				response_2 = prediction_engine.data_analysis().predict_test_case_success_rate(csv_path, csv_file, kwargs)
				if not response_2:
					return init_predictions_stats_generator.error_text('ERROR: Could NOT retrieve historical data for predictions')
					
				list_predicted_test_case_run_time.append(response_1)
				list_predicted_test_case_success_rate.append(response_2)
					
				return prediction_engine.data_analysis().get_prediction_stats(csv_path, project, application, batch_name, list_tcids, dict_run_time_stats_by_host_count, list_predicted_test_case_run_time, list_predicted_test_case_success_rate)
