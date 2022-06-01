FROM nikolaik/python-nodejs:python3.10-nodejs17

RUN mkdir /app
WORKDIR /app
ENV PORT 8080

COPY package*.json ./
COPY requirements.txt ./

RUN pip install -r requirements.txt
RUN npm install

COPY . /app

CMD ["npm", "start"]