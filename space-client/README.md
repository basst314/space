# Space Client

## Getting Started
After Checkout / `git clone`, these commands will get you up and running:

```shell
# go to space-client subdirectory
cd space-client/

# install dependencies
npm install

# run development build and start a local server
npm start

# the space-client will be running on http://127.0.0.1:3000
```
Local changes on the code will automagically trigger a rebuild and reload of the app in the browser, as long as the `npm start` command runs in the shell.

To execute the above commands, Node.js needs to be installed on your machine, which can de download here: https://nodejs.org/en/download/


## Local Build
The Space-Client is built using npm and webpack. 

There are two main build options:
 * Development Build
 * Production Build

**Development Build.** Triggering the command `npm run build` will trigger a regular development build with webpack. Sources will be transpiled, bundled and injected into the `index.html` file. This build option is used when running `npm start`.

**Production Build.** Running the command `npm run build:prod` will trigger the production-ready build. In addition to the development build, the sources will be mangled, minimized and uglified. This will result in a much smaller file size.
This build option is used when running the build on travis.

## Build on Travis

The build on travis is configured in the `.travis.yml` file in the root directory of the Space-Project. It builds the `space-client` by running the `npm run build:prod` command and runs karma- and e2e-tests in a headless browser.
After that, the `space-common` and `space-server` projects are built using a regular gradle build. Finally, the `space-server` integrates the built `space-client` into its fat-jar to be ready to be deployed.

