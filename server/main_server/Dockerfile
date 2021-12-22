FROM tensorflow/tensorflow:latest

RUN apt-get update
RUN python3 -m pip install --upgrade pip setuptools wheel
RUN apt-get install ffmpeg libsm6 libxext6  -y

WORKDIR /app
COPY requirements.txt .

RUN pip3 install -r requirements.txt

COPY model/labels.txt ./model/labels.txt
COPY resources ./resources
COPY sleaf.py .
COPY classifier.py .
COPY __init__.py .

EXPOSE 5000
EXPOSE 8080

CMD exec gunicorn --bind :8080 --workers 1 --threads 8 --timeout 0 sleaf:app
