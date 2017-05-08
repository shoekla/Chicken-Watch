from flask import Flask
from flask import request
from flask import render_template
import time
import scare
import os
app = Flask(__name__)

@app.route('/movieChicken/<name>/')
def getMovieDeets(name):
	return scare.scrape(str(name))

if __name__ == '__main__':
	app.run(debug=True)