import { Routes } from '@angular/router';
import { NoContentComponent } from './no-content/no-content.component';
import { DebugClientComponent } from './debug-client/debug-client.component';
import { WsClientComponent } from './ws-client/ws-client.component';

export const ROUTES: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'wsclient'},

  {path: 'debug', component: DebugClientComponent},

  {path: 'wsclient', component: WsClientComponent},

  {path: '**', component: NoContentComponent},
];
