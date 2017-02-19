/**
 * Space Debug-Client
 * Created by basst314 on 02.01.2017.
 */
import { Component, OnInit } from '@angular/core';
import { SpaceDebugHttpService } from './service/space-debug-http.service';
import { SpaceWorld } from '../domain/space-world';

@Component({
  selector: 'debug-client',
  templateUrl: 'debug-client.component.html',
  styleUrls: [
    'debug-client.component.css'
  ]
})
export class DebugClientComponent implements OnInit {
  public world: SpaceWorld;
  public msgs: string[] = [];

  constructor(private spaceDebugService: SpaceDebugHttpService) {
  }

  public ngOnInit(): void {
    this.startGame();
  }

  public stepWorld(): void {
    this.spaceDebugService.doStep()
      .then((world: SpaceWorld) => {
        this.log("stepWorld() successful");
        this.world = world;
      })
      .catch((e) => this.handleError(e));
  }

  public space(): void {
    this.spaceDebugService.sendSpace()
      .then(() => this.log("space() successful"))
      .catch((e) => this.handleError(e));
  }

  public doubleSpace(): void {
    this.spaceDebugService.sendDoubleSpace()
      .then(() => this.log("doubleSpace() successful"))
      .catch((e) => this.handleError(e));
  }

  public tripleSpace(): void {
    this.spaceDebugService.sendTripleSpace()
      .then(() => this.log("tripleSpace() successful"))
      .catch((e) => this.handleError(e));
  }

  public startGame(): void {
    this.spaceDebugService.startGame()
      .then((world: SpaceWorld) => {
        this.log("startGame() successful");
        this.world = world;
      })
      .catch((e) => this.handleError(e));
  }

  public stopGame(): void {
    this.spaceDebugService.stopGame()
      .then(() => this.log("stopGame() successful"))
      .catch((e) => this.handleError(e));
  }

  private handleError(e) {
    this.log("ERROR: " + (e.message || e));
  }

  private log(msg: string) {
    this.msgs.unshift(msg + "\n");
  }

  get messages(): string[] {
    return this.msgs;
  }

}
