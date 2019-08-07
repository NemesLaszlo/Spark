import { User } from './user';

export class Message {
    public id: number;
    public sender: User;
    public sendTime: Date;
    public message: string;
}
