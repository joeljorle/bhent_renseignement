import { IUser } from 'app/core/user/user.model';
import { Musictype } from 'app/shared/model/enumerations/musictype.model';

export interface IProfile {
  id?: number;
  sexe?: string;
  age?: number;
  residentState?: string;
  phone?: number;
  artistName?: string;
  categorie?: Musictype;
  otherMusicType?: string;
  eventParticipation?: boolean;
  eventParticipationName?: string;
  experienceTime?: number;
  user?: IUser;
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public sexe?: string,
    public age?: number,
    public residentState?: string,
    public phone?: number,
    public artistName?: string,
    public categorie?: Musictype,
    public otherMusicType?: string,
    public eventParticipation?: boolean,
    public eventParticipationName?: string,
    public experienceTime?: number,
    public user?: IUser
  ) {
    this.eventParticipation = this.eventParticipation || false;
  }
}
