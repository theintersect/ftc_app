import React from 'react'
import LogItem from './logItem.jsx'

const Log = props => {
  const { log } = props
  const { logItems } = log

  const logStyles = { fontFamily: 'Consolas', fontSize: '12px' }

  return (
    <div className="card">
      <div className="card-body">
        <h5 className="card-title">Log</h5>
        <ul className="list-group" style={logStyles}>
          {logItems.map(item => {
            return (
              <LogItem
                key={item.id}
                label={item.label}
                message={item.message}
              />
            )
          })}
        </ul>
      </div>
    </div>
  )
}

export default Log
