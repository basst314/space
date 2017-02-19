import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { SpaceWorld } from '../../domain/space-world';
import { SpaceHttpApiUrl } from './space-http-api-url';

/**
 * Service for accessing the space debug web api
 * Created by basst314 on 02.01.2017.
 */
@Injectable()
export class SpaceDebugHttpService {

  constructor(private http: Http) {
  }

  /**
   * invokes a game-world step on the server
   */
  public getWorld(): Promise<SpaceWorld> {
    return this.http
      .get(SpaceHttpApiUrl.world)
      .map((res) => res.json())
      .toPromise();
  }

  /**
   * invokes a game-world step on the server
   */
  public doStep(): Promise<SpaceWorld> {
    return this.http
      .get(SpaceHttpApiUrl.step)
      .map((res) => res.json())
      .toPromise();
  }

  /**
   * Sends a SPACE-Event to the server api
   */
  public sendSpace(): Promise<void> {
    return this.http
      .get(SpaceHttpApiUrl.space)
      .map((res) => null)
      .toPromise();
  }

  /**
   * Sends a DOUBLESPACE-Event to the server api
   */
  public sendDoubleSpace(): Promise<void> {
    return this.http
      .get(SpaceHttpApiUrl.doubleSpace)
      .map((res) => null)
      .toPromise();
  }

  /**
   * Sends a TRIPLESPACE-Event to the server api
   */
  public sendTripleSpace(): Promise<void> {
    return this.http
      .get(SpaceHttpApiUrl.tripleSpace)
      .map((res) => null)
      .toPromise();
  }

  /**
   * Starts a new game
   */
  public startGame(): Promise<SpaceWorld> {
    return this.http
      .get(SpaceHttpApiUrl.startGame)
      .map((res) => res.json())
      .toPromise();
  }

  /**
   * Stops the active game
   */
  public stopGame(): Promise<void> {
    return this.http
      .get(SpaceHttpApiUrl.stopGame)
      .map((res) => null)
      .toPromise();
  }

}
