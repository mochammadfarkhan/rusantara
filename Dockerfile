FROM nikolaik/python-nodejs:python3.10-nodejs17
RUN mkdir /project
WORKDIR /project
COPY requirements.txt /project/requirements.txt

RUN pip install -r requirements.txt
COPY . /project/

RUN mkdir /opt/app
WORKDIR /opt/app
COPY package*.json ./

RUN npm install

COPY . /opt/app

CMD ["npm", "start"]