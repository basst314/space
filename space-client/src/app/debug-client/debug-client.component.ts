/**
 * Space Debug-Client
 * Created by basst314 on 02.01.2017.
 */
import {Component, OnInit} from "@angular/core";
import {SpaceDebugService} from "./space-debug.service";
import {SpaceWorld} from "../domain/SpaceWorld";

@Component({
  selector: 'debug-client',
  templateUrl: 'debug-client.component.html',
  styleUrls: [
    'debug-client.component.css'
  ]
})
export class DebugClientComponent implements OnInit {
  world: SpaceWorld;
  msgs: Array<string> = [];

  constructor(private spaceDebugService: SpaceDebugService) {
  }

  ngOnInit(): void {
    this.startGame();
  }

  stepWorld(): void {
    this.spaceDebugService.doStep()
      .then((world: SpaceWorld) => {
        this.log("stepWorld() successful");
        this.world = world;
      })
      .catch(e => this.handleError(e));
  }

  space(): void {
    this.spaceDebugService.sendSpace()
      .then(() => this.log("space() successful"))
      .catch(e => this.handleError(e));
  }

  doubleSpace(): void {
    this.spaceDebugService.sendDoubleSpace()
      .then(() => this.log("doubleSpace() successful"))
      .catch(e => this.handleError(e));
  }

  tripleSpace(): void {
    this.spaceDebugService.sendTripleSpace()
      .then(() => this.log("tripleSpace() successful"))
      .catch(e => this.handleError(e));
  }

  startGame(): void {
    this.spaceDebugService.startGame()
      .then((world: SpaceWorld) => {
        this.log("startGame() successful");
        this.world = world;
      })
      .catch(e => this.handleError(e));
  }

  stopGame(): void {
    this.spaceDebugService.stopGame()
      .then(() => this.log("stopGame() successful"))
      .catch(e => this.handleError(e));
  }

  private handleError(e) {
    this.log("ERROR: " + (e.message || e));
  }

  private log(msg: string) {
    this.msgs.unshift(msg + "\n");
  }

  get messages(): Array<string> {
    return this.msgs;
  }

}
