import { User } from './user';
import { Message } from './message';

export class Match {
    public id: number;
    public userA: User;
    public userB: User;
    public messages: Message[];
}
