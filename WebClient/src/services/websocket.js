export function wsConnect(dispatch) {
  const ws = new WebSocket("ws://localhost:49300/websocket");

  ws.onopen = () => {
   
  };

  ws.onmessage = ({ data }) => {
    let result = JSON.parse(data);
    let type = result.componentName;
    switch (type) {
      case "bots":
        dispatch({ type: "timer/getBots", payload: { page: 1 } });
        break;
      case "resourceTable":
        let time = new Date().getTimezoneOffset() / 60;
        dispatch({
          type: "timer/fleshResource",
          payload: { requestTimeZone: time }
        });
        break;
      default:
        break;
    }
  };

  ws.onclose = function(e) {
  
  };
}
