import tensorflow as tf
import keras
from keras.models import load_model
from keras.preprocessing import image
import numpy as np
import sys

model = load_model('./model/rusantara_model.h5')

def predict():
  img_path = 'uploads/' + sys.argv[1];
  img = image.load_img(img_path, target_size=(160, 160))
  img = image.img_to_array(img)
  img = np.expand_dims(img, axis=0)
  img = np.vstack([img]) 

  result = model.predict(img)

  foods = ['dadar gulung', 'lapis legit', 'onde-onde']

  prediction = foods[np.argmax(result)]

  print(prediction)

predict()
