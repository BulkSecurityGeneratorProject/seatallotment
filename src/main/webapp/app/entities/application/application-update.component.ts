import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IApplication, Application } from 'app/shared/model/application.model';
import { ApplicationService } from './application.service';

@Component({
  selector: 'jhi-application-update',
  templateUrl: './application-update.component.html'
})
export class ApplicationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    application_no: [],
    applicant_name: [],
    applicant_email: [],
    applicant_mobile: [],
    applicant_dob: [],
    applicant_address: [],
    course: [],
    institute: [],
    speciality: [],
    course_doc: [],
    first_preference: [],
    second_preference: [],
    third_preference: [],
    entryby: [],
    entry_date: [],
    status: []
  });

  constructor(protected applicationService: ApplicationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ application }) => {
      this.updateForm(application);
    });
  }

  updateForm(application: IApplication) {
    this.editForm.patchValue({
      id: application.id,
      application_no: application.application_no,
      applicant_name: application.applicant_name,
      applicant_email: application.applicant_email,
      applicant_mobile: application.applicant_mobile,
      applicant_dob: application.applicant_dob != null ? application.applicant_dob.format(DATE_TIME_FORMAT) : null,
      applicant_address: application.applicant_address,
      course: application.course,
      institute: application.institute,
      speciality: application.speciality,
      course_doc: application.course_doc,
      first_preference: application.first_preference,
      second_preference: application.second_preference,
      third_preference: application.third_preference,
      entryby: application.entryby,
      entry_date: application.entry_date != null ? application.entry_date.format(DATE_TIME_FORMAT) : null,
      status: application.status
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const application = this.createFromForm();
    if (application.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationService.update(application));
    } else {
      this.subscribeToSaveResponse(this.applicationService.create(application));
    }
  }

  private createFromForm(): IApplication {
    return {
      ...new Application(),
      id: this.editForm.get(['id']).value,
      application_no: this.editForm.get(['application_no']).value,
      applicant_name: this.editForm.get(['applicant_name']).value,
      applicant_email: this.editForm.get(['applicant_email']).value,
      applicant_mobile: this.editForm.get(['applicant_mobile']).value,
      applicant_dob:
        this.editForm.get(['applicant_dob']).value != null
          ? moment(this.editForm.get(['applicant_dob']).value, DATE_TIME_FORMAT)
          : undefined,
      applicant_address: this.editForm.get(['applicant_address']).value,
      course: this.editForm.get(['course']).value,
      institute: this.editForm.get(['institute']).value,
      speciality: this.editForm.get(['speciality']).value,
      course_doc: this.editForm.get(['course_doc']).value,
      first_preference: this.editForm.get(['first_preference']).value,
      second_preference: this.editForm.get(['second_preference']).value,
      third_preference: this.editForm.get(['third_preference']).value,
      entryby: this.editForm.get(['entryby']).value,
      entry_date:
        this.editForm.get(['entry_date']).value != null ? moment(this.editForm.get(['entry_date']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplication>>) {
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
