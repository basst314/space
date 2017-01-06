/**
 * URLs to space api
 * Created by basst314 on 02.01.2017.
 */
export class SpaceApiUrl {
  public static baseUrl: string = SPACE_API_BASEURL;

  public static world: string = SpaceApiUrl.baseUrl + '/world';
  public static step: string = SpaceApiUrl.baseUrl + '/step';
  public static space: string = SpaceApiUrl.baseUrl + '/space';
  public static doubleSpace: string = SpaceApiUrl.baseUrl + '/doublespace';
  public static tripleSpace: string = SpaceApiUrl.baseUrl + '/tripplespace';
  public static startGame: string = SpaceApiUrl.baseUrl + '/start';
  public static stopGame: string = SpaceApiUrl.baseUrl + '/stop';

}
