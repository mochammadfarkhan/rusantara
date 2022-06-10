package com.capstone.rusantara.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("emailVerified")
	val emailVerified: Boolean,

	@field:SerializedName("metadata")
	val metadata: Metadata,

	@field:SerializedName("providerData")
	val providerData: List<ProviderDataItem>,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("disabled")
	val disabled: Boolean,

	@field:SerializedName("tokensValidAfterTime")
	val tokensValidAfterTime: String,

	@field:SerializedName("email")
	val email: String
)

data class ProviderDataItem(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("providerId")
	val providerId: String,

	@field:SerializedName("email")
	val email: String
)

data class Metadata(

	@field:SerializedName("lastSignInTime")
	val lastSignInTime: Any,

	@field:SerializedName("creationTime")
	val creationTime: String
)
