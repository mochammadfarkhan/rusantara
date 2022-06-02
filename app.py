import tensorflow as tf
import keras
from keras.models import load_model
from tensorflow.keras.utils import img_to_array, load_img
import numpy as np
import sys

model = load_model('./model/rusantara_model.h5')

def predict():
  img_path = 'uploads/' + sys.argv[1]
  img = load_img(img_path, target_size=(160, 160))
  img = img_to_array(img)
  img = np.expand_dims(img, axis=0)
  img = np.vstack([img])

  result = model.predict(img, verbose=0)

  foods = ['clorot', 'dadar gulung', 'klepon', 'lapis legit', 'onde-onde', 'putu ayu']

  prediction = foods[np.argmax(result[0])]

  print(prediction)

predict()
