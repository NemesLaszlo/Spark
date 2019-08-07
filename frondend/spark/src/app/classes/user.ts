import { Picture } from './picture';

export class User {
    
    public id: number;
    public fullName: string;
    public password: string;
    public email: string;
    public description: string;
    public lastOnline: Date;
    public gender: string;
    public sexuality: string;
    public role: string;
    public pictures: Picture[];
    
}
