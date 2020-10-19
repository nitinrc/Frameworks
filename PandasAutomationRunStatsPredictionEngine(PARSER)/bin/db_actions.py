#!/usr/bin/python python3.7.1
# -*- coding: <encoding name> -*-

import sybpydb
import db2
import logging

class db_actions:
	connection = None
	
	@classmethod
	def get_connection(cls, db_type, server, db_name, new=False):
		# Creates new singleton database connection
		if new or not cls.connection:
			cls.connection = cls.connect_to_db(db_type, server, db_name)
		return cls.connection
	
	@classmethod
	def connect_to_db(cls, db_type, server, db_name):
		db_conn = ''
		if db_type == 'sybase':
			try:
				db_conn = sybpydb.connect(dsn="servername=%s;database=%s" % (server, db_name))
				break
			except Exception as e:
				logging.info('Could NOT connect to db')
		elif db_type == 'db2':
			try:
				db_conn = db2.connect(server)
				break
			except Exception as e:
				logging.info('Could NOT connect to db')
		
		return db_conn
	
	@classmethod
	def get(cls, db_type, server, db_name, query):
		db_conn = cls.get_connection(db_type, server, db_name)
		db_cursor = db_conn.cursor()
		db_cursor.execute(query)
		records = db_cursor.fetchall()
		db_cursor.close()
		return records
		
	@classmethod
	def post(cls, db_type, server, db_name, query):
		db_conn = cls.get_connection(db_type, server, db_name)
		db_cursor = db_conn.cursor()
		db_cursor.execute(query)
		db_conn.commit()
		db_cursor.close()
		return records
			
	def get_db_cursor(self, db_conn):
		return db_conn.cursor()
	
	def close_db_conn(self, db_conn, db_cursor):
		db_cursor.close()
		del db_cursor
		db_conn.close()
		
	def get_table_col_mapping(self, db_type, server, db_name, table_name):
		dict_cols = {}
		db_conn = self.connect_to_db(db_type, server, db_name)
		db_cursor = self.get_db_cursor(db_conn)
		db_cursor.execute("select * from " + table_name)
		description = db_cursor.description
		for index_column in range(len(description)):
			dict_cols[description[index_column][0]] = index_column
		self.close_db_conn(db_conn, db_cursor)
		return dict_cols
