import urllib2
import re
import requests
import string
from bs4 import BeautifulSoup
from urllib2 import urlopen

def scrape(movie):
	pages = []
	source_code = requests.get("http://wheresthejump.com/") #Gets source Code
	plain_text = source_code.text
	soup = BeautifulSoup(plain_text,"html.parser")
	for link in soup.findAll('a'):

		s = str(link)
		if s.lower() in movie or movie in s.lower():
			return getMovieInfo(str(link.get("href")))
	return "Nothing"
def getMovieInfo(url):
	res = []
	source_code = requests.get(url) #Gets source Code
	plain_text = source_code.text
	#soup = BeautifulSoup(plain_text,"html.parser")
	beg = plain_text.find("Jump Scares:")
	jumpScares = plain_text[plain_text.find(">",beg)+1:plain_text.find("</p>",beg)]

	res = jumpScares[1:]+","
	begA = plain_text.find("Jump Scare Rating:")
	plain_text = plain_text[plain_text.find("Jump Scare Times</em>"):]
	soup = BeautifulSoup(plain_text,"html.parser")
	for link in soup.findAll('p'):
		time = str(link)
		if "<em>" not in time:
			res = res + time[3:time.find(" ")].replace("<strong>","") + ","
	return res[:-1]
#arr = scrape("http://wheresthejump.com/", "insidious: chapter 2")
#for i in arr:
#	print i
#print scrape("get out")