/**
 * URLs to space api
 * Created by basst314 on 02.01.2017.
 */
export class SpaceApiUrl {
  static baseUrl: string = SPACE_API_BASEURL;

  static world: string = SpaceApiUrl.baseUrl + '/world';
  static step: string = SpaceApiUrl.baseUrl + '/step';
  static space: string = SpaceApiUrl.baseUrl + '/space';
  static doubleSpace: string = SpaceApiUrl.baseUrl + '/doublespace';
  static tripleSpace: string = SpaceApiUrl.baseUrl + '/tripplespace';
  static startGame: string = SpaceApiUrl.baseUrl + '/start';
  static stopGame: string = SpaceApiUrl.baseUrl + '/stop';

}
