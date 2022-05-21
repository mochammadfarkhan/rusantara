import tensorflow as tf
import keras
from keras.models import load_model
from keras.preprocessing import image
import numpy as np
import sys

model = load_model('./model/classifier.h5')

def predict():
  img_path = 'uploads/' + sys.argv[1];
  img = image.load_img(img_path, target_size=(64, 64))
  img = image.img_to_array(img)
  img = np.expand_dims(img, axis=0)
  img = np.vstack([img]) 

  result = model.predict(img)

  {'dogs': 1, 'cats': 0}
  if result[0][0] == 1:
    prediction = 'dog'
  else:
    prediction = 'cat'

  print(prediction)

predict()
