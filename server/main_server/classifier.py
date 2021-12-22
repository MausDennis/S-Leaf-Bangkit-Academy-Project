import tensorflow as tf
import cv2
import numpy as np
import requests
import json

MODEL_PATH = 'model/custom_bgmodify'
LABEL_PATH = 'model/labels.txt'

class Classifier:
    def __init__(self):
        self.model = tf.keras.models.load_model(MODEL_PATH)
        self.class_index = self._get_label_index(LABEL_PATH)
    
    def _get_label_index(self, filename):
        class_index = {}

        with open(filename, 'r') as f:
            lines = f.readlines()
            class_index = {i:name.strip() for i,name in enumerate(lines)}
        return class_index
    
    def _preprocess(self, img):
        img = cv2.resize(img, (224,224))
        img = img.astype(np.float32) / 255.0
        return img

    def predict(self, img):
        img_prep = self._preprocess(img)
        img_prep = np.expand_dims(img_prep, axis=0)
        print(img_prep.shape)
        # data = json.dumps({"signature_name": "serving_default", "instances": img_prep.tolist()})
        # headers = {"content-type": "application/json"}
        # json_response = requests.post('http://172.17.0.2:8501/v1/models/resnet50_bgmodify:predict', data=data, headers=headers)
        # predictions = json.loads(json_response.text)['predictions']
        predictions = self.model.predict(img_prep)
        pred = predictions[0]
        y = np.argmax(pred)
        return {'class':y,
                'name': self.class_index[y],
                'score': pred[y]}
        
        # ### Dummy
        # return {'class' : 5,
        #         'name' : self.class_index[5],
        #         'score' : 0.98
        #     }

    def get_numclasses(self):
        return len(self.class_index)
