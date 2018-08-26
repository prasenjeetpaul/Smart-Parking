# -*- coding: utf-8 -*-
"""
Created on Sun Aug 26 11:48:20 2018

@author: Deepak_Reddy
"""

# importing the requests library
import requests
import numpy as np
import random


  


def main():
    # defining the api-endpoint 
    API_ENDPOINT = "http://10.71.12.253:85/predict_full.php"
    
    
    minutes = predict()
    time = '{:02d}:{:02d}:00'.format(*divmod(minutes, 60))
    # data to be sent to api
    data = {'estimatedFullTime':time}
 
    # sending post request and saving response as response object
    r = requests.post(url = API_ENDPOINT, data = data)
 
    # extracting response text 
    print(predict())
    print(r)
    print(time)

if __name__== "__main__":
  main()











########### MODEL ##############
def getModel():
    #Parking Slot Data
    X_Time = random.sample(range(0, 1200), 150)
    X_Time = np.sort(X_Time)
    y_slotsRemaining = np.flip(np.arange(1,51),0)
    y_slotsRemaining = np.pad(y_slotsRemaining, (0, 50), 'constant')
    y_slotsRemaining = np.pad(y_slotsRemaining, (50, 0), 'edge')
    #Reshaping Arrays
    X_Time = X_Time.reshape(-1,1)
    y_slotsRemaining = y_slotsRemaining.reshape(-1,1)
    # Fitting Linear Regression to the dataset
    from sklearn.linear_model import LinearRegression
    lin_reg = LinearRegression()
    lin_reg.fit(X_Time, y_slotsRemaining)
    # Fitting Polynomial Regression to the dataset
    from sklearn.preprocessing import PolynomialFeatures
    poly_reg = PolynomialFeatures(degree = 4)
    X_poly = poly_reg.fit_transform(X_Time)
    poly_reg.fit(X_poly, y_slotsRemaining)
    lin_reg_2 = LinearRegression()
    lin_reg_2.fit(X_poly, y_slotsRemaining)
    return lin_reg_2,poly_reg


############ FULL TIME PREDICTION ##########
def predict():
    #Calculating Estimated Fill Time
    log_reg,poly_reg = getModel()
    for x in range(0, 1440):
        temp =  log_reg.predict(poly_reg.fit_transform(x))
        if temp<1 : 
            timeInMinutes = x;
            break;
    #Converting Minuts to Hours
    return timeInMinutes


############ 