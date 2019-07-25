import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInstitute, Institute } from 'app/shared/model/institute.model';
import { InstituteService } from './institute.service';

@Component({
  selector: 'jhi-institute-update',
  templateUrl: './institute-update.component.html'
})
export class InstituteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    type: []
  });

  constructor(protected instituteService: InstituteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ institute }) => {
      this.updateForm(institute);
    });
  }

  updateForm(institute: IInstitute) {
    this.editForm.patchValue({
      id: institute.id,
      name: institute.name,
      type: institute.type
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const institute = this.createFromForm();
    if (institute.id !== undefined) {
      this.subscribeToSaveResponse(this.instituteService.update(institute));
    } else {
      this.subscribeToSaveResponse(this.instituteService.create(institute));
    }
  }

  private createFromForm(): IInstitute {
    return {
      ...new Institute(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      type: this.editForm.get(['type']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstitute>>) {
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
