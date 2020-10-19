#!/usr/bin/python python3.7.1
# -*- coding: <encoding name> -*-

import os.path
import os, sys,inspect
import argparse
import importlib.util
import ast
import json
from json import loads
from flask import Flask, request, jsonify
import flask
from flask import abort

import stats_prediction_service

app = Flask(__name__)
app.debug = True

@app.route("/PARSER/getPredictionStats/<string:project>,<string:application>,<string:batch_name>,<string:batch_id>,<path:csv_path>", methods=['GET'])
def get_prediction_stats(project, application, batch_name, batch_id, csv_path):
	try:
		return stats_prediction_service.generate_prediction_stats(self, project, application, batch_name, batch_id, csv_path)
	except:
		abort(400)
