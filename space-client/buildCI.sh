#!/bin/bash

export DISPLAY=:99.0
sh -e /etc/init.d/xvfb start
sleep 3

set -o errexit # Exit on error

npm install

npm run lint
npm test
npm run build:prod

sh -c 'npm run server:prod:ci' &
sleep 3
npm run e2e

