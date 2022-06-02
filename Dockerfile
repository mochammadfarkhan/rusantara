# FROM nikolaik/python-nodejs:python3.10-nodejs17

# COPY requirements.txt .
# RUN pip3 install -r requirements.txt

# WORKDIR /usr/src/app
# COPY package*.json ./

# RUN npm install --only=production

# COPY . ./
# # RUN apt-get update \
# #   && apt-get install python -y \
# #   && apt-get install pip \
# #   && pip install -r requirements.txt

# # WORKDIR /usr/src/app
# # COPY package*.json ./

# # COPY . ./

# CMD [ "npm", "start" ]

FROM python:3

# WORKDIR /usr/src/app

# COPY requirements.txt ./
# COPY package*.json ./

# RUN pip install --no-cache-dir -r requirements.txt \
#   && apt-get update \
#   && apt-get install nodejs -y \
#   && npm install

# COPY . ./

# CMD [ "npm", "start" ]

FROM python:3.10 as build

WORKDIR /opt/app
RUN python -m venv /opt/app/venv
ENV PATH="/opt/app/venv/bin:$PATH"

COPY requirements.txt .
RUN pip install -r requirements.txt

FROM node:17

RUN apt update \
    && apt install software-properties-common -y \
    && add-apt-repository ppa:deadsnakes/ppa -y \
    && apt update \
    && apt install python3.10 -y

WORKDIR /opt/app
COPY --from=build /opt/app/venv /venv

ENV PATH="/opt/app/venv/bin:$PATH"
ENV NODE_ENV=container

COPY package*.json .
RUN npm install

COPY . .
CMD npm start