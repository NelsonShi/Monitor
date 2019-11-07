export default{
  extraBabelPlugins: [
   ["import", { "libraryName": "antd", "libraryDirectory": "es", "style": "css" }]
  ],
 
  proxy: {
  "/bpServer": {
    "target": "http://localhost:8080/",
    "changeOrigin": true,
    "pathRewrite": { "^/bpServer" : "" }
   },
   "/localServer": {
    "target": "http://localhost:8080/",
    "changeOrigin": true,
    "pathRewrite": { "^/localServer" : "" }
   }
  },

  define: {
    'process.env': {},
    'process.env.NODE_ENV': process.env.NODE_ENV,
    'process.env.API_ENV': process.env.API_ENV,
   },
  
}
