import { Routes } from '@angular/router';
import { NoContentComponent } from './no-content/no-content.component';
import { DebugClientComponent } from './debug-client/debug-client.component';
import { WebSocketClientComponent } from './web-socket-client/web-socket-client.component';

export const ROUTES: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'wsclient'},

  {path: 'debug', component: DebugClientComponent},

  {path: 'wsclient', component: WebSocketClientComponent},

  {path: '**', component: NoContentComponent},
];
