import { Injectable } from '@angular/core';
import { WebSocketService } from '../../shared/services/web-socket.service';
import { Subject } from 'rxjs';
import { WebSocketEvent } from '../../domain/web-socket-event';

/**
 * Service for accessing the space-server webSocket-API
 */
@Injectable()
export class SpaceWebSocketService {
  private static getBaseUrl() {
    let protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://';
    let host = window.location.hostname;
    let port = ':' + window.location.port;

    return protocol + host + port;
  }

  public messages: Subject<WebSocketEvent>;

  constructor(private webSocketService: WebSocketService) {
    let wsUrl = SPACE_WS_API_BASEURL;

    if (wsUrl == null || (!wsUrl.startsWith("ws://") && !wsUrl.startsWith("wss://"))) {
      // build absolute url from relative path
      wsUrl = SpaceWebSocketService.getBaseUrl() + SPACE_WS_API_BASEURL;
    }
    console.debug("connecting to space websocket api: " + wsUrl);

    this.messages = <Subject<WebSocketEvent>> webSocketService
      .connect(wsUrl)
      .map((response: MessageEvent): WebSocketEvent => {
        console.log('received ws data: ' + response.data);
        return JSON.parse(response.data);
      });
  }

}
