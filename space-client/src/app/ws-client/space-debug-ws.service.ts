import { Injectable } from '@angular/core';
import { WebSocketService } from '../shared/services/websocket.service';
import { Subject } from 'rxjs';
import { WebSocketEvent } from '../domain/WebSocketEvent';

/**
 * Service for accessing the space-server debug webSocket-API
 */
@Injectable()
export class SpaceDebugWsService {
  public messages: Subject<WebSocketEvent>;

  constructor(private webSocketService: WebSocketService) {
    let wsUrl = SPACE_WS_API_BASEURL.replace('http://', 'ws://');

    this.messages = <Subject<WebSocketEvent>> webSocketService
      .connect(wsUrl)
      .map((response: MessageEvent): WebSocketEvent => {
        console.log('received ws data: ' + response.data);
        return JSON.parse(response.data);
      });
  }

}
