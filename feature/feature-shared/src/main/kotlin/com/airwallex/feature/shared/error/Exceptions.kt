package com.airwallex.feature.shared.error

import com.airwallex.core.basic.model.AirwallexException

/*************************************************************
 *                     generic exceptions                    *
 *************************************************************/
data class GenericException(override val cause: Throwable? = null) : AirwallexException(message = "Oops, something went wrong.", cause = cause)


/*************************************************************
 *                     navigation exceptions                 *
 *************************************************************/
object NavRouteActionNotFoundException : AirwallexException("Nav route action not found.")
object NavArgNotFoundException : AirwallexException("Nav arguments is not found in route path.")

/*************************************************************
 *                     mvi exceptions                      *
 *************************************************************/

data class IllegalPreviousViewStateException(val viewStateName: String) : AirwallexException("Previous View state $viewStateName is illegal here.")
data class IllegalViewStateException(val viewStateName: String) : AirwallexException("View state $viewStateName is illegal here.")
