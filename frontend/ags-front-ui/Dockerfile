FROM node:22.14.0-alpine AS builder

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ENV PATH /usr/src/app/node_modules/.bin:${PATH}
COPY package*.json /usr/src/app/
RUN npm install

COPY . /usr/src/app

RUN npm run build

FROM nginx:1.28.0-alpine

COPY --from=builder /usr/src/app/dist /usr/share/nginx/html

RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/conf.d

CMD ["nginx", "-g", "daemon off;"]
