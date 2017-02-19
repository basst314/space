import { Directive, HostListener } from '@angular/core';
import { spaceKeyCode, SpaceEventService } from '../service/space-event.service';

@Directive({
  selector: '[space-listener]',
})
export class SpaceListenerDirective {

  constructor(private _spaceEventService: SpaceEventService) {
  }

  @HostListener('document:keypress', ['$event'])
  public keyPressed(event: KeyboardEvent) {
    if (event && event.keyCode === spaceKeyCode) {
      if (event.target === document.body) {
        // prevent event bubbling
        event.preventDefault();
      }
      this._spaceEventService.notifyControlEvent(event);
    }
  }

}
