const ngApimock= require('ng-apimock')();
const express = require('express');
const app = express();

ngApimock.run({
    watch: true,
    src: './mocks/requests',
    outputDir: './tmp/ngApiMock',
    done: function () {
    }
});

app.set('port', parseInt(process.env.PORT) || 4000);

app.use(require('ng-apimock/lib/utils').ngApimockRequest);
app.use('/mocking', express.static('./tmp/ngApimock'));

app.listen(app.get('port'), function () {
    console.log('mock server running on port', app.get('port'));
});