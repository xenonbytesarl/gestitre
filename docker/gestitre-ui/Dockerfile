#Build
FROM node:20.16 AS build

ARG ARG_APP_PROFILE=local

ENV ENV_APP_PROFILE=${ARG_APP_PROFILE}

WORKDIR /app

COPY package*.json ./

RUN npm install
COPY . .
RUN npm run build-${ENV_APP_PROFILE}

#Runtime
FROM nginx:1.26.1-alpine

COPY nginx.conf /etc/ngnix/nginx.conf
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80 443 5173