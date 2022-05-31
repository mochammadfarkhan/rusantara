import tensorflow as tf
import keras
from keras.models import load_model
from keras.preprocessing import image
import numpy as np
import sys

model = load_model('./model/rusantara_model.h5')

def predict():
  img_path = './images/resep-onde-onde.jpg'
  img = image.load_img(img_path, target_size=(160, 160))
  img = image.img_to_array(img)
  img = np.expand_dims(img, axis=0)
  img = np.vstack([img])

  result = model.predict(img)

  foods = ['clorot', 'dadar gulung', 'klepon', 'lapis legit', 'onde-onde', 'putu ayu']

  prediction = foods[np.argmax(result[0])]

  print(prediction)

predict()
