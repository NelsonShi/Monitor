export default{
  extraBabelPlugins: [
   ["import", { "libraryName": "antd", "libraryDirectory": "es", "style": "css" }]
  ],
 
  proxy: {
  "/bpServer": {
    "target": "http://localhost:49200/",
    "changeOrigin": true,
    "pathRewrite": { "^/bpServer" : "" }
   },
   "/localServer": {
    "target": "http://127.0.0.1:7001/",
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
