import React from 'react'

const LogItem = props => {
  return (
    <li className="list-group-item p-2">
      {formatLogItem(props.label, props.message)}
    </li>
  )
}

const formatLogItem = (label, message) => {
  return `[${label}]: ${message}`
}

export default LogItem
