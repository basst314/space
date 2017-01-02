import {browser, by, element} from "protractor";

describe('App', () => {

  beforeEach(() => {
    browser.get('/');
  });


  it('should have a title', () => {
    let subject = browser.getTitle();
    let result = 'The.Space.Game';
    expect(subject).toEqual(result);
  });

  it('should have navbar-brand', () => {
    let subject = element(by.css('.navbar-brand')).isPresent();
    let result = true;
    expect(subject).toEqual(result);
  });

});
