# import http request lib
import requests
# import json lib
import json

# Parameters
# API key
API_KEY = "6e926ef705ba758fe71d04dbb47f7482"
# latitude
LAT = "48.289989133430964"
# longitude
LON = "6.942186381923429"
# units
UNITS = "metric"
# language
LANG = "fr"
# API URL
URL = "https://api.openweathermap.org/data/2.5/forecast?lat="+LAT+"&lon="+LON+"&appid="+API_KEY +"&units="+UNITS+"&lang="+LANG

# Get data from API
response = requests.get(URL)
# Convert data to json
data = json.loads(response.text)

# get weather data for the next 5 days
for i in range(0, 40):
    # get date
    date = data["list"][i]["dt_txt"]
    # get temperature
    temp = data["list"][i]["main"]["temp"]
    # get weather description
    desc = data["list"][i]["weather"][0]["description"]
    # get weather icon
    icon = data["list"][i]["weather"][0]["icon"]
    # print data
    print(date + " - " + str(temp) + "°C - " + desc + " - " + icon)
