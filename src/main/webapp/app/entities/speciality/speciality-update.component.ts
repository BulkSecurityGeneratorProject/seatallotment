import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISpeciality, Speciality } from 'app/shared/model/speciality.model';
import { SpecialityService } from './speciality.service';

@Component({
  selector: 'jhi-speciality-update',
  templateUrl: './speciality-update.component.html'
})
export class SpecialityUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    active: []
  });

  constructor(protected specialityService: SpecialityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ speciality }) => {
      this.updateForm(speciality);
    });
  }

  updateForm(speciality: ISpeciality) {
    this.editForm.patchValue({
      id: speciality.id,
      name: speciality.name,
      active: speciality.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const speciality = this.createFromForm();
    if (speciality.id !== undefined) {
      this.subscribeToSaveResponse(this.specialityService.update(speciality));
    } else {
      this.subscribeToSaveResponse(this.specialityService.create(speciality));
    }
  }

  private createFromForm(): ISpeciality {
    return {
      ...new Speciality(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpeciality>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
