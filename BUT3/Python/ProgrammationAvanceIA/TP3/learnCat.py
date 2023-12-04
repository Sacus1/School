import pandas as pd

# NumPy - mathematical functions on multi-dimensional arrays and matrices
import numpy as np

# Matplotlib - plotting library to create graphs and charts
# import matplotlib.pyplot as plt

# TensorFlow - end-to-end open source platform for machine learning.
import tensorflow as tf

# Keras - high-level API for TensorFlow
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D
from tensorflow.keras.layers import Activation, Dropout, Flatten, Dense
from tensorflow.keras.preprocessing.image import ImageDataGenerator

# OS module for Python
import os
os.environ['KMP_DUPLICATE_LIB_OK']='True'

import matplotlib.pyplot as plt
def showImages(arr):
     fig, axes = plt.subplots(1, 5, figsize=(20, 20))
     axes = axes.flatten()
     for img, ax in zip(arr, axes):
         ax.imshow(img)
     plt.tight_layout()
     plt.show()

train_dir = './data/train'
validate_dir = './data/validation'
test_dir = './data/test'

train_dogs_dir = os.path.join(train_dir, 'dogs')
train_cats_dir = os.path.join(train_dir, 'cats')
validate_dogs_dir = os.path.join(validate_dir, 'dogs')
validate_cats_dir = os.path.join(validate_dir, 'cats')
test_cats_and_dogs_dir = os.path.join(test_dir, 'cats_and_dogs')

num_dogs_train = len(os.listdir(train_dogs_dir))
num_cats_train = len(os.listdir(train_cats_dir))
num_dogs_validate = len(os.listdir(validate_dogs_dir))
num_cats_validate = len(os.listdir(validate_cats_dir))

train_total = num_dogs_train + num_cats_train
validate_total = num_dogs_validate + num_cats_validate
test_total = len(os.listdir(test_cats_and_dogs_dir))

batch_size = 32
img_size = 150

img_gen = ImageDataGenerator(rescale=1./255)

train_img_gen = img_gen.flow_from_directory(batch_size=batch_size,
                                           directory=train_dir,
                                           shuffle=True,
                                           target_size=(img_size, img_size),
                                           class_mode='binary')

validate_img_gen = img_gen.flow_from_directory(batch_size=batch_size,
                                               directory=validate_dir,
                                               shuffle=False,
                                               target_size=(img_size, img_size),
                                               class_mode='binary')

test_img_gen = img_gen.flow_from_directory(batch_size=batch_size,
                                               directory=test_dir,
                                               shuffle=False,
                                               target_size=(img_size, img_size),
                                               class_mode=None)
                                               
                                               
#showImages([train_img_gen[0][0][0] for i in range(15)])
 
model = Sequential()

model.add(Conv2D(32, (3, 3), input_shape=(150, 150, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(64, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(128, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(128, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Flatten())
model.add(Dense(256, activation='relu'))

model.add(Dropout(0.5))

model.add(Dense(1, activation='sigmoid'))

model.compile(loss='binary_crossentropy',
              optimizer='rmsprop', # rmsprop or adam
              metrics=['accuracy'])
              
model.summary()

fit_result = model.fit_generator(
            train_img_gen,
            steps_per_epoch=int(np.ceil(train_total / float(batch_size))),
            epochs=5,
            validation_data=validate_img_gen,
            validation_steps=int(np.ceil(validate_total / float(batch_size)))
            )
            
model.save_weights('model.h5')


# Epoch 1/5
# 625/625 [==============================] - 1180s 2s/step - loss: 0.6481 - accuracy: 0.6230 - val_loss: 0.5420 - val_accuracy: 0.7317
# Epoch 2/5
# 625/625 [==============================] - 1350s 2s/step - loss: 0.4994 - accuracy: 0.7608 - val_loss: 0.4313 - val_accuracy: 0.8135
# Epoch 3/5
# 625/625 [==============================] - 1162s 2s/step - loss: 0.4132 - accuracy: 0.8159 - val_loss: 0.3671 - val_accuracy: 0.8396
# Epoch 4/5
# 625/625 [==============================] - 1188s 2s/step - loss: 0.3588 - accuracy: 0.8458 - val_loss: 0.4088 - val_accuracy: 0.8371
# Epoch 5/5
# 625/625 [==============================] - 1197s 2s/step - loss: 0.3179 - accuracy: 0.8662 - val_loss: 0.3378 - val_accuracy: 0.8529
