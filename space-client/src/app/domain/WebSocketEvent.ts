import { SpaceWorld } from './SpaceWorld';
/**
 * Domain class for a space websocket event
 */
export interface WebSocketEvent {
  playerId?: number;
  worldId?: number;

  worldEventType: WorldEventType;

  world?: SpaceWorld;
}

export type WorldEventType = 'SPACE' | 'DOUBLE_SPACE' | 'TRIPPLE_SPACE' | 'STEP' | 'START' | 'STOP' | 'UPDATE';
