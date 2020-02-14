import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    sexe: [],
    age: [],
    residentState: [],
    phone: [],
    artistName: [],
    categorie: [],
    otherMusicType: [],
    eventParticipation: [],
    eventParticipationName: [],
    experienceTime: [],
    user: []
  });

  constructor(
    protected profileService: ProfileService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(profile: IProfile): void {
    this.editForm.patchValue({
      id: profile.id,
      sexe: profile.sexe,
      age: profile.age,
      residentState: profile.residentState,
      phone: profile.phone,
      artistName: profile.artistName,
      categorie: profile.categorie,
      otherMusicType: profile.otherMusicType,
      eventParticipation: profile.eventParticipation,
      eventParticipationName: profile.eventParticipationName,
      experienceTime: profile.experienceTime,
      user: profile.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      age: this.editForm.get(['age'])!.value,
      residentState: this.editForm.get(['residentState'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      artistName: this.editForm.get(['artistName'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
      otherMusicType: this.editForm.get(['otherMusicType'])!.value,
      eventParticipation: this.editForm.get(['eventParticipation'])!.value,
      eventParticipationName: this.editForm.get(['eventParticipationName'])!.value,
      experienceTime: this.editForm.get(['experienceTime'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
