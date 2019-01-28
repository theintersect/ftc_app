var request = require("request-promise");


var options = { method: 'GET',
  url: 'https://calicosiotest.myshopify.com/products.json',
  qs: { cache: 'false' },
  headers:
   { authorization: 'Basic YzFmOTIxOGEyYThkMjljNGQzNDdmMzllMTljNzA5NTQ6',
     cache: 'XmlHttpRequest',
     'content-type': 'gzip' },
};


  setInterval(() => {
    request(options).then(body => {
        console.log(body)
    })
  },0)
