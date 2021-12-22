from flask import Flask, request, jsonify, send_from_directory
from PIL import Image
import numpy as np
from classifier import Classifier
import os
import json
import base64
import io
import cv2

app = Flask(__name__)
classifier = Classifier()
app.config["INFO_DIR"] = 'resources/informasi'
app.config["IMG_DIR"] = 'resources/wiki_images_baru'

@app.route('/', methods=['GET'])
def home():
    return "Welcome to S-LEAF!"

@app.route("/upload", methods=["POST"])
def process_image():
    # print(request.form['image'])
    data = request.form['image']
    image_data = base64.decodebytes(data.encode())
    img = Image.open(io.BytesIO(image_data))

    # # Save the image to storage
    # file_path = 'out.jpg'
    # with open(file_path, 'wb') as f:
    #     f.write(image_data)

    img_np = np.array(img)

    result = classifier.predict(img_np)
    
    info = get_info(result['class'])
    return jsonify(info)

@app.route('/random', methods=['GET'])
def random():
    infos = []
    classes = np.random.randint(0,30, 3)
    for class_idx in classes:
        info = get_info(int(class_idx))
        infos.append(info)
    return jsonify(infos)

@app.route('/all', methods=['GET'])
def get_all_info():
    infos = []
    for idx in range(classifier.get_numclasses()):
        info = get_info(idx)
        infos.append(info)
    return jsonify(infos)

def get_info(class_idx):
    path = os.path.join(app.config["INFO_DIR"], str(class_idx)+'.json')
    with open(path, 'r') as f:
        info = json.load(f)
    info['id'] = int(class_idx)
    return info

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=(int(os.environ.get("PORT", 8080))))
