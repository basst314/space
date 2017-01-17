/**
 * URLs to space http-api
 * Created by basst314 on 02.01.2017.
 */
export class SpaceHttpApiUrl {
  public static baseUrl: string = SPACE_HTTP_API_BASEURL;

  public static world: string = SpaceHttpApiUrl.baseUrl + '/world';
  public static step: string = SpaceHttpApiUrl.baseUrl + '/step';
  public static space: string = SpaceHttpApiUrl.baseUrl + '/space';
  public static doubleSpace: string = SpaceHttpApiUrl.baseUrl + '/doublespace';
  public static tripleSpace: string = SpaceHttpApiUrl.baseUrl + '/tripplespace';
  public static startGame: string = SpaceHttpApiUrl.baseUrl + '/start';
  public static stopGame: string = SpaceHttpApiUrl.baseUrl + '/stop';

}
