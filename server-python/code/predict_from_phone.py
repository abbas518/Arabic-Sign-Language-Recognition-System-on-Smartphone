#!/usr/bin/env python2
import os
import sys
import traceback
import sys,time 
import socket


import cv2
from sklearn.externals import joblib

from common.config import get_config
from common.image_transformation import apply_image_transformation

def main():

    model_name = 'svm'
    
    model_serialized_path = get_config(
        'model_{}_serialized_path'.format(model_name))
    
    testing_images_labels_path = get_config('testing_images_labels_path')
    with open(testing_images_labels_path, 'r') as file:
        lines = file.readlines()
        for line in lines:
            image_path, image_label = line.split()
            frame = cv2.imread(image_path)
            try:
                frame = apply_image_transformation(frame)
                frame_flattened = frame.flatten()
                classifier_model = joblib.load(model_serialized_path)
                predicted_labels = classifier_model.predict(frame_flattened.reshape(1, -1))
                predicted_label = predicted_labels[0]
                print("Predicted label = {}".format(predicted_label))
                if image_label != predicted_label:
                    print("Incorrect prediction!!")
                    cv2.waitKey(5000)
            except Exception:
                exception_traceback = traceback.format_exc()
                print("Error while applying image transformation on image path '{}' with the following exception trace:\n{}".format(
                    image_path, exception_traceback))
                continue
    cv2.destroyAllWindows()
    print ("The program completed successfully !!")
def getData():
    server_socket.listen(5)
    print("Server started... Waiting for connection...")
    client_socket, address = server_socket.accept()
    print ("Conencted to - ",address,"\n")
    dst="images\\01.jpg"
    with open(dst,'wb') as fp:
       print("file opened")
       buf = ''
       while len(buf)<4:
          buf = client_socket.recv(4).decode(encoding="latin-1")
       while True: 
          strng = client_socket.recv(4856)
          if not strng:
             break
          else:
             fp.write(strng)
    print("Data Received successfully")
    fp.close()
    client_socket.close()

if __name__ == '__main__':
    print("starting server...")
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(("", 13085))
    
    while True:
        getData()
        main()
    
