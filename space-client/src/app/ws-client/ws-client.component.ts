import { Component, OnInit } from '@angular/core';
import { SpaceDebugHttpService } from './space-debug-http.service';
import { SpaceWorld } from '../domain/SpaceWorld';
import { SpaceDebugWsService } from './space-debug-ws.service';
import { WorldEventType, WebSocketEvent } from '../domain/WebSocketEvent';

/**
 * Space WebSocket-Client
 * Created by basst314 on 02.01.2017.
 */
@Component({
  selector: 'ws-client',
  templateUrl: 'ws-client.component.html',
  styleUrls: [
    'ws-client.component.css'
  ]
})
export class WsClientComponent implements OnInit {
  public playerId: number = 0;
  public worldId: number = 0;
  public world: SpaceWorld;
  public msgs: string[] = [];

  constructor(private spaceDebugService: SpaceDebugWsService) {
    this.spaceDebugService.messages.subscribe((event: WebSocketEvent) => {
      this.log("received event: " + event.worldEventType);
      if (event.worldEventType === 'UPDATE') {
        this.world = event.world;
      } else {
        this.log("ERROR: unknown event");
      }
    });
  }

  public ngOnInit(): void {
    // this.startGame();
  }

  public stepWorld(): void {
    this.spaceDebugService.messages.next(this.getEvent('STEP'));
  }

  public space(): void {
    this.spaceDebugService.messages.next(this.getEvent('SPACE'));
  }

  public doubleSpace(): void {
    this.spaceDebugService.messages.next(this.getEvent('DOUBLE_SPACE'));
  }

  public tripleSpace(): void {
    this.spaceDebugService.messages.next(this.getEvent('TRIPPLE_SPACE'));
  }

  public startGame(): void {
    this.log("starting game... playerId: " + this.playerId + ", worldId: " + this.worldId);
    this.spaceDebugService.messages.next(this.getEvent('START'));
  }

  public stopGame(): void {
    this.spaceDebugService.messages.next(this.getEvent('STOP'));
  }

  private log(msg: string) {
    this.msgs.unshift(msg + "\n");
  }

  get messages(): string[] {
    return this.msgs;
  }

  private getEvent(type: WorldEventType): WebSocketEvent {
    let event = {
      playerId: this.playerId,
      worldId: this.worldId,
      worldEventType: type
    };
    console.debug("sending event:", event);

    return event;
  }

}
